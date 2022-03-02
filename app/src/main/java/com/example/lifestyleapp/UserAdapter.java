package com.example.lifestyleapp;

import android.content.DialogInterface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifestyleapp.databinding.RowUserBinding;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RowUserBinding binding;
        private UserAdapter adapter;
        User user;

        public UserViewHolder(View view, UserAdapter adapter) {
            super(view);
            user = ProfileFragment.users.get(Math.max(getAdapterPosition(), 0));
            binding = RowUserBinding.bind(view);
            updateText();
            binding.buttonSet.setOnClickListener(this);
            binding.buttonEdit.setOnClickListener(this);
            binding.buttonDelete.setOnClickListener(this);
            this.adapter = adapter;
        }

        public void updateText() {
            binding.textViewName.setText(user.firstname + " " + user.lastname);
            binding.textViewAge.setText(user.age < 0 ? "Profile Incomplete" : user.age + " Years Old");
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonSet:
                    ProfileFragment.user = user;
                    HomeFragment.updateInfo();
                    FitnessFragment.updateInfo();
                    break;
                case R.id.buttonEdit:
                    ProfileFragment.user = user;
                    ProfileFragment profilePopup = new ProfileFragment();
                    profilePopup.setCancelable(true);
                    profilePopup.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), null);
                    ((AppCompatActivity) view.getContext()).getSupportFragmentManager().executePendingTransactions();
                    profilePopup.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            updateText();
                        }
                    });
                    break;
                case R.id.buttonDelete:
                    if (ProfileFragment.users.size() <= 1)
                        Toast.makeText(view.getContext(), "Cannot delete last profile", Toast.LENGTH_SHORT).show();
                    else {
                        adapter.notifyItemRemoved(getAdapterPosition());
                        ProfileFragment.users.remove(user);
                        ProfileFragment.user = ProfileFragment.users.get(0);
                    }
                    break;
            }
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent,false);
        return new UserViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.user = ProfileFragment.users.get(position);
        holder.updateText();
    }

    @Override
    public int getItemCount() {
        return ProfileFragment.users.size();
    }
}
