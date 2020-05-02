package me.fleka.modernandroidapp.uimodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import me.fleka.modernandroidapp.BR

/**
 * Created by Mladen Rakonjac on 8/13/17.
 */
class Repository(repositoryName : String, var repositoryOwner: String?, var numberOfStars: Int?
                 , var hasIssues: Boolean = false) : BaseObservable(){

    @get:Bindable
    var repositoryName : String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.repositoryName)
    }

}