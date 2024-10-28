package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employee> employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView roleTextView;
        private TextView salaryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvName);
            roleTextView = itemView.findViewById(R.id.tvRole);
            salaryTextView = itemView.findViewById(R.id.tvSalary);
        }

        public void bind(Employee employee) {
            nameTextView.setText(employee.getName());
            roleTextView.setText(employee.getRole());
            salaryTextView.setText(employee.getSalary());
        }
    }
}
