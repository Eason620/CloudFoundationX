package com.ziyao.cfx.im.api;

import com.ziyao.cfx.im.AutoIMConfigurationImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ziyao zhang
 * @since 2023/7/5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoIMConfigurationImportSelector.class)
public @interface EnabledIM {
}
