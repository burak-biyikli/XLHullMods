package com.XLHullMods.plugins;


import com.fs.starfarer.api.BaseModPlugin;

import lunalib.lunaRefit.LunaRefitManager;
import lunalib.backend.ui.refit.RefitButtonAdder;

import com.XLHullMods.ui.RefitTest;


public class XLHullModsUIPlugin extends BaseModPlugin {

    @Override
    public void onApplicationLoad() {
        LunaRefitManager.addRefitButton(new RefitTest());
    }
}
