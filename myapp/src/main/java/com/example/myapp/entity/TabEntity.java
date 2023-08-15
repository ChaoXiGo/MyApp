package com.example.myapp.entity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
class ViewHolder extends RecyclerView.ViewHolder{

    private final View id;
    private final View username;
    private final View password;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        username = itemView.findViewById(R.id.username);
        password = itemView.findViewById(R.id.password);
    }
}
