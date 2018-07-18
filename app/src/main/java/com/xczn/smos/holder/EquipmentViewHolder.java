package com.xczn.smos.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.xczn.smos.R;

/**
 * Created by zhangxiao
 * Date on 2018/5/4.
 */
public class EquipmentViewHolder extends TreeNode.BaseNodeViewHolder<EquipmentViewHolder.EquipmentItem> {

    public EquipmentViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, EquipmentViewHolder.EquipmentItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_item_equipment, null, false);

        TextView tvFactoryName = view.findViewById(R.id.tree_factory_name);
        tvFactoryName.setText(value.factoryName);
        return view;
    }

    @Override
    public void toggle(boolean active) {
    }

    public static class EquipmentItem {
        //public int icon;

        public String factoryName;

        public EquipmentItem(String name) {
            this.factoryName = name;
        }
    }
}

