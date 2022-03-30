package com.example.lifestyleapp;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifestyleapp.databinding.RowUserBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private UsersViewModel model;

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowUserBinding binding;
        private UserAdapter adapter;

        public UserViewHolder(View view, UserAdapter adapter) {
            super(view);
            this.adapter = adapter;

            binding = RowUserBinding.bind(view);

            update(Math.max(getAdapterPosition(), 0));

            binding.buttonSet.setOnClickListener(this);
            binding.buttonEdit.setOnClickListener(this);
            binding.buttonDelete.setOnClickListener(this);
        }

        public void update(User user) {
            if (binding != null) {
                String name = String.format("%s %s", user.firstname, user.lastname);
                binding.textViewName.setText(name);
                binding.textViewAge.setText(user.age < 0 ? "Profile Incomplete" : user.age + " Years Old");
            }
        }

        public void update(int index) {
            update(adapter.model.getUserList().getValue().get(index));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonSet:
                    adapter.model.setUser(Math.max(getAdapterPosition(), 0));
                    break;

                case R.id.buttonEdit:
                    adapter.model.setUser(Math.max(getAdapterPosition(), 0));
                    ProfileFragment profilePopup = new ProfileFragment();
                    profilePopup.setCancelable(true);
                    profilePopup.show(((AppCompatActivity) view.getContext()).getSupportFragmentManager(), null);
                    ((AppCompatActivity) view.getContext()).getSupportFragmentManager().executePendingTransactions();
                    profilePopup.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            update(adapter.model.getUser().getValue());
                        }
                    });
                    break;
                case R.id.buttonDelete:
                    if (adapter.model.getUserList().getValue().size() <= 1)
                        Toast.makeText(view.getContext(), "Cannot delete last profile", Toast.LENGTH_SHORT).show();
                    else {
                        adapter.notifyItemRemoved(getAdapterPosition());
                        adapter.model.delete(Math.max(getAdapterPosition(), 0));
                    }
                    break;
            }
        }
    }

    public UserAdapter(UsersViewModel model) {
        this.model = model;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent,false);
        return new UserViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return model.getUserList().getValue().size();
    }
}
