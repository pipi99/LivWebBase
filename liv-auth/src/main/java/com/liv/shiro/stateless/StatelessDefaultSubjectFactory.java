package com.liv.shiro.stateless;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: Subject工厂 ,无状态
 * @date 2020.4.21  09:18
 * @email 453826286@qq.com
 */
public class StatelessDefaultSubjectFactory  extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
