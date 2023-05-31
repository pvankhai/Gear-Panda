package com.pvkhai.gearpandabackend.services.impl;

import com.pvkhai.gearpandabackend.constants.ThymeleafConstants;
import com.pvkhai.gearpandabackend.services.ThymeleafService;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Service
public class ThymeleafServiceImpl implements ThymeleafService {
    private static TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(ThymeleafConstants.MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(ThymeleafConstants.MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(ThymeleafConstants.MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(ThymeleafConstants.UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * Get content of file Thymeleaf
     *
     * @return
     */
    @Override
    public String getContent(String name) {
        String nameUser = name;
         Context context = new Context();
        return templateEngine.process(ThymeleafConstants.TEMPLATE_NAME, context);
    }
}
