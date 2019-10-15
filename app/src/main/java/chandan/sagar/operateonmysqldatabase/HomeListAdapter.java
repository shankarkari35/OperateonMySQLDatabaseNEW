package chandan.sagar.operateonmysqldatabase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{
    Context mContext;
    int w=5;
    ArrayList<HashMap<String, String>> mArray;

    public HomeListAdapter(Context cxt, ArrayList<HashMap<String, String>> mArray){
        this.mContext = cxt;
        this.mArray = mArray;
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView statusT,nameT,idT,dateT;
        public ViewHolder(View v){
            super(v);
            dateT =  v.findViewById(R.id.txtdate);
            statusT =v.findViewById(R.id.txtstatus);
            nameT = v.findViewById(R.id.txtname);
            idT =  v.findViewById(R.id.txtid);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String,String> map = mArray.get(position);
        holder.nameT.setText(map.get("service_name"));
        holder.idT.setText(map.get("date"));
        holder.statusT.setText(map.get("status"));
        holder.dateT.setText(map.get("date"));

        // MainActivity.description1=map.get("detail");
    }

    @Override
    public int getItemCount()
    {
        return mArray.size(

        );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false);
        ViewHolder vh = new ViewHolder(mRowView);

        return vh;
    }
}

