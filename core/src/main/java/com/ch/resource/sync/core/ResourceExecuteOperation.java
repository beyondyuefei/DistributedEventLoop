package com.ch.resource.sync.core;

import java.util.function.Consumer;

/**
 *  资源变更执行的语义泛化接口，即：不关心是增加还是减少，只做执行
 */
public interface ResourceExecuteOperation extends ResourceOperation{
    // todo Consumer是否需要单独再封装为一个确定语义的函数接口 ？
    <T> void execute(final ResourceKey resourceKey, final Consumer<T> consumer);
}
