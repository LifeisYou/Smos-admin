package com.xczn.smos.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.xczn.smos.R;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by zhangxiao
 * Date on 2018/5/4.
 */
public class FactoryViewHolder extends TreeNode.BaseNodeViewHolder<FactoryViewHolder.FactoryItem> {

    private PrintView arrowView;
    public FactoryViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, FactoryViewHolder.FactoryItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.tree_item_factory, null, false);

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

    public static class FactoryItem {
        //public int icon;

        public String factoryName;

        public FactoryItem(String name) {
            this.factoryName = name;
        }
    }
}
