package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Constructor;
import java.util.List;

import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Pedido;
import ar.edu.utn.frsf.dam.isi.laboratorio02.modelo.Producto;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoHolder> {

    private Context ctx;
    private List<Pedido> datos;

    public PedidoAdapter(Context context, List<Pedido> objects){
        super(context, 0, objects);

        this.ctx = context;
        this.datos = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.ctx);

        View fila02 = convertView;
        if(fila02 == null) {
            inflater.inflate(R.layout.fila_historial, parent, false);
        }
        PedidoHolder holder = (PedidoHolder) fila02.getTag();
        if(holder==null){
            holder = new PedidoHolder(fila02);
            fila02.setTag(holder);
        }

        Pedido p = (Pedido) super.getItem(position);
        holder.tvMailPedido.setText(p.getMailContacto());
        holder.tvPrecio.setText(p.total().toString());
        holder.tvCantidadItems.setText(p.getDetalle().size());

        if(p.getRetirar()) holder.tipoEntrega.setImageResource(R.drawable.ic_restaurant);
        else holder.tipoEntrega.setImageResource(R.drawable.ic_truck);

        holder.estado.setText(p.getEstado().toString());

        switch (p.getEstado()){
            case LISTO:
                holder.estado.setTextColor(Color.DKGRAY);
                break;
            case ENTREGADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
            case CANCELADO:
            case RECHAZADO:
                holder.estado.setTextColor(Color.RED);
                break;
            case ACEPTADO:
                holder.estado.setTextColor(Color.GREEN);
                break;
            case EN_PREPARACION:
                holder.estado.setTextColor(Color.MAGENTA);
                break;
            case REALIZADO:
                holder.estado.setTextColor(Color.BLUE);
                break;
        }

        holder.tvHoraEntrega.setText(p.getFecha().toString());

        new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int indice = (int) v.getTag();

                Pedido pedidoSeleccionado = datos.get(indice);
                if(pedidoSeleccionado.getEstado().equals(Pedido.Estado.REALIZADO) ||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.ACEPTADO) ||
                        pedidoSeleccionado.getEstado().equals(Pedido.Estado.EN_PREPARACION)){
                    pedidoSeleccionado.setEstado(Pedido.Estado.CANCELADO);
                    PedidoAdapter.this.notifyDataSetChanged();
                }
            }
        };

        //configuramos la vista
        return fila02;
    }

    @NonNull
    @Override
    public PedidoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoHolder pedidoHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
