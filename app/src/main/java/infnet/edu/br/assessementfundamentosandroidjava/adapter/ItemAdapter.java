package infnet.edu.br.assessementfundamentosandroidjava.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import infnet.edu.br.assessementfundamentosandroidjava.R;
import infnet.edu.br.assessementfundamentosandroidjava.model.Tarefa;

/**
 * Created by joaoluisdomingosxavier on 28/09/17.
 */

public class ItemAdapter extends ArrayAdapter {
    private ArrayList<Tarefa> tarefas;
    private Context context;

    public ItemAdapter(@NonNull Context context, @NonNull ArrayList<Tarefa> objects) {
        super(context, 0, objects);
        this.context = context;
        this.tarefas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.details_list, parent, false);
        }

        TextView txt_id = (TextView) view.findViewById(R.id.text_id);
        TextView txt_description = (TextView) view.findViewById(R.id.text_description);

        Tarefa tarefa = tarefas.get(position);

        txt_id.setText(tarefa.getId());
        txt_description.setText(tarefa.getDescricao());

        return view;
        //return super.getView(position, convertView, parent);
    }
}
