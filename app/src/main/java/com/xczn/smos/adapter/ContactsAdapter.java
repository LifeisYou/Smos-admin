package com.xczn.smos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xczn.smos.R;
import com.xczn.smos.entity.UsersPinyin;
import com.xczn.smos.listener.OnItemClickListener;
import com.xczn.smos.utils.GlideUtils;
import com.xczn.smos.utils.SharedPreferencesUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author zhangxiao
 * @Date 2018/4/23 0023
 * @Comment
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private List<UsersPinyin> contacts;
    private int layoutId;
    private OnItemClickListener mClickListener;
    private Context mContext;

    public ContactsAdapter(List<UsersPinyin> contacts, int layoutId) {
        this.contacts = contacts;
        this.layoutId = layoutId;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //Context
        this.mContext = parent.getContext();
        View view = inflater.inflate(layoutId, null);
        final ContactsViewHolder holder = new ContactsViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        UsersPinyin contact = contacts.get(position);
        if (position == 0 || !contacts.get(position-1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
        Glide.with(mContext).load(
                SharedPreferencesUtils.getInstance().getBaseUrl()+"avatars?id="+contact.getUserId()+".jpg")
                .apply(GlideUtils.optionsNoCache)
                .into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        CircleImageView ivAvatar;
        TextView tvName;

        ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_index);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
