package edu.neu.cs5520.numad22su_group19_techtalk.listeners;

import android.view.View;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.ResourceItemBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.Resource;

public interface OnResourceClickListener {
//    void OnResourceClick(View view, Resource resource, ResourceItemBinding resourceItemBinding);
    void OnResourceClick(Resource resource);

}
