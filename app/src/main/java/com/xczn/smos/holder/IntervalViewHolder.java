package com.xczn.smos.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.xczn.smos.R;

/**
 * Created by zhangxiao
 * Date on 2018/5/4.
 */
public class IntervalViewHolder extends TreeNode.BaseNodeViewHolder<IntervalViewHolder.IntervalItem> {

    private PrintView arrowView;

    public IntervalViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IntervalViewHolder.IntervalItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_item_interval, null, false);

        TextView tvFactoryName = view.findViewById(R.id.tree_factory_name);
        tvFactoryName.setText(value.factoryName);

        arrowView = view.findViewById(R.id.arrow_icon);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IntervalItem {
        //public int icon;

        String factoryName;

        public IntervalItem(String name) {
            this.factoryName = name;
        }
    }
}

