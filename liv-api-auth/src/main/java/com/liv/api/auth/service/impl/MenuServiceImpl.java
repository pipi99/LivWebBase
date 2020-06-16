package com.liv.api.auth.service.impl;

import com.google.common.collect.Lists;
import com.liv.api.auth.dao.MenuMapper;
import com.liv.api.auth.dao.RoleMapper;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.MenuDO;
import com.liv.api.auth.service.MenuService;
import com.liv.api.auth.shiro.stateless.filters.StatelessAccessControlFilter;
import com.liv.api.auth.shiro.stateless.filters.StatelessBasicHttpAuthenticationFilter;
import com.liv.api.auth.shiro.stateless.filters.StatelessPermissionsFilter;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.base.BaseService;
import com.liv.api.base.utils.LivCllectionUtils;
import com.liv.api.base.utils.LivContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiMenuService")
public class MenuServiceImpl extends BaseService<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    /**
     * @Author: LiV
     * @Date: 2020.6.5 14:54
     * @Description: 处理菜单权限过滤器
     **/
    @PostConstruct
    private void initMenuPermission(){
        this.setPermissionFilter();
    }

    @Override
    public void setPermissionFilter() {

        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

        //shiro配置过滤规则少量的话可以用hashMap,数量多了要用LinkedHashMap,保证有序
        Map<String, String> chains = new LinkedHashMap<>();

        /**自动弹出登录框*/
        chains.put("/swagger-ui.html", "BasicHttpAuthenticationFilter");
        chains.put("/swagger-ui.html/**", "BasicHttpAuthenticationFilter");
        chains.put("/swagger-resources/**", "BasicHttpAuthenticationFilter");
        chains.put("/swagger/**", "BasicHttpAuthenticationFilter");
        chains.put("/v2/**", "BasicHttpAuthenticationFilter");
        chains.put("/v2/**", "BasicHttpAuthenticationFilter");
        chains.put("/webjars/**", "BasicHttpAuthenticationFilter");
        chains.put("/configuration/ui", "BasicHttpAuthenticationFilter");
        chains.put("/configuration/security", "BasicHttpAuthenticationFilter");
        chains.put("/csrf", "anon");

        /**无需登录访问**/
        chains.put("/**/*.js", "anon");
        chains.put("/**/*.png", "anon");
        chains.put("/**/*.jpg", "anon");
        chains.put("/**/*.css", "anon");

        List<Menu> menus = this.mapper.selectList(null);
        for (int i = 0; i <menus.size() ; i++) {
            Menu menu = menus.get(i);
            String url = menu.getMUrl();
            //菜单链接为空
            if(StringUtils.isEmpty(url)){
                continue;
            }
            String accessCtrl = menu.getAccessCtrl();

            if(url.endsWith("/")||url.endsWith("\\")){
                url = url.substring(0,url.length()-1);
            }

            //公开访问的链接
            if(AppConst.MENU_OPEN.equals(accessCtrl)){
                chains.put(url+"/**", "anon");
            }
        }

        for (int i = 0; i <menus.size() ; i++) {
            Menu menu = menus.get(i);
            String url = menu.getMUrl();
            //菜单链接为空
            if(StringUtils.isEmpty(url)){
                continue;
            }
            String accessCtrl = menu.getAccessCtrl();
            if(url.endsWith("/")||url.endsWith("\\")){
                url = url.substring(0,url.length()-1);
            }
            //授权才可访问的菜单
            if(AppConst.MENU_PERM.equals(accessCtrl)){
                chains.put(url+"/**", "StatelessAuthcFilter,PermissionsFilter");
            }
        }

        //登录后访问的链接
        chains.put("/**", "StatelessAuthcFilter");

        // 清空老的权限控制
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chains);

        // 重新构建生成
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }
    }

    /**
     * 获取当前登录用户的菜单列表
     **/
    @Override
    public List<MenuDO> getCurUserMenus() throws Exception {
        //无需赋权即可访问的链接
        List<MenuDO> result = mapper.findNoPermMenus();


        //当前登录用户信息
        UserVO user = ApiAuthUtils.getCurrentUser().getUser();
        Long userId = user.getUserId();


        //当前用户组
        List<Groups> groups = userMapper.findGroups(userId);
        List<Role> roles = Lists.newArrayList();
        if(groups.size()>0){
            //用户组角色
            List<Long> groupIds = groups.stream().map(Groups::getGroupId).collect(Collectors.toList());
            List<Role> groupRoles = roleMapper.findByGroupIds(groupIds);
            roles.addAll(groupRoles);
        }

        //用户角色
        List<Role> userRoles = roleMapper.findByUserId(userId);
        roles.addAll(userRoles);

        //角色菜单查询
        if(roles.size()>0){
            //组角色+用户角色菜单
            List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
            List<MenuDO> result2 =  mapper.findRoleMenus(roleIds);
            result.addAll(result2);
        }

        //用户菜单
        List<MenuDO> result1 = mapper.findCurUserMenus(userId);
        result.addAll(result1);

        return LivCllectionUtils.getTree(result,"id","parentId","children");
    }
}
