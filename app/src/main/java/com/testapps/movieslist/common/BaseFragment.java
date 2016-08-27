package com.testapps.movieslist.common;


import android.app.Fragment;

import com.testapps.movieslist.di.IHasComponent;

public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }
}
