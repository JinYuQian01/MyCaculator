package com.example.mycaculator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycaculator.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class MyCalculatorAdapter extends RecyclerView.Adapter<MyCalculatorAdapter.InnerHolder> {

    private List<String> myCalculatorButtonLists=new ArrayList<>();
    private OnCalculatorItemClickListener onCalculatorItemClickListener;

    public MyCalculatorAdapter(List<String> myCalculatorButtonLists) {
        this.myCalculatorButtonLists = myCalculatorButtonLists;
    }

    //暴露接口
    public void setOnCalculatorItemClickListener(OnCalculatorItemClickListener onCalculatorItemClickListener){
        this.onCalculatorItemClickListener=onCalculatorItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //创建每一个Item,找到View
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculator_button,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InnerHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.setData(myCalculatorButtonLists.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCalculatorItemClickListener != null) {
                    onCalculatorItemClickListener.ItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (myCalculatorButtonLists != null) {
            return myCalculatorButtonLists.size();
        }else {
            return 0;
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        private TextView tvFormula;
        public InnerHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
        }
        //找到每个Item的控件，设置相关数据
        public void setData(String s){
            //找到相关的控件
            TextView tvButtonItem=itemView.findViewById(R.id.calculator_button_item);

            //设置数据
            tvButtonItem.setText(s);
        }
    }

    //设置点击的接口
    public interface OnCalculatorItemClickListener {
        void ItemClick(int position);
    }
}