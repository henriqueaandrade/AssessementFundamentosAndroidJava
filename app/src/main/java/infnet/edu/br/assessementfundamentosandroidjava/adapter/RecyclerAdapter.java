package infnet.edu.br.assessementfundamentosandroidjava.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.model.Tarefa;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Tarefa> tarefas;

    public RecyclerAdapter(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_id.setText(tarefas.get(position).getId());
        holder.text_description.setText(tarefas.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_id;
        TextView text_description;
        public MyViewHolder(View itemView) {
            super(itemView);
            text_id = itemView.findViewById(R.id.text_id);
            text_description = itemView.findViewById(R.id.text_description);
        }
    }
}
