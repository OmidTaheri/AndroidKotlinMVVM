package ir.omidtaheri.androidkotlinmvvm.data

import ir.omidtaheri.androidkotlinmvvm.data.network.ApiHelper
import ir.omidtaheri.androidkotlinmvvm.data.pref.PreferencesHelper

interface DataManager :PreferencesHelper,ApiHelper {
}