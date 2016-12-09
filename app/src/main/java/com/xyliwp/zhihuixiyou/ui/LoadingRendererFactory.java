package com.xyliwp.zhihuixiyou.ui;

/**
 * Created by lwp940118 on 2016/10/26.
 */
import android.content.Context;
import android.util.SparseArray;

import java.lang.reflect.Constructor;

public final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {
        //circle jump
        LOADING_RENDERERS.put(0, SwapLoadingRenderer.class);
        LOADING_RENDERERS.put(1, CircleBroodLoadingRenderer.class);
        LOADING_RENDERERS.put(2, DayNightLoadingRenderer.class);
        LOADING_RENDERERS.put(3, GearLoadingRenderer.class);
        LOADING_RENDERERS.put(4, CollisionLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(Context context, int loadingRendererId) throws Exception {
        Class<?> loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes != null
                    && parameterTypes.length == 1
                    && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}
