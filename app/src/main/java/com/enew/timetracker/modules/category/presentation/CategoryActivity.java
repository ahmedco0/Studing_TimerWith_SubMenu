package com.enew.timetracker.modules.category.presentation;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.enew.timetracker.R;
import com.enew.timetracker.commons.presentation.BaseActivity;
import com.enew.timetracker.commons.presentation.presenter.RecyclerItemClickListener;
import com.enew.timetracker.commons.presentation.presenter.SpaceItemDecoration;
import com.enew.timetracker.modules.category.models.CategoryModel;
import com.enew.timetracker.modules.category.presentation.presenter.CategoriesAdapter;

import org.apache.commons.lang3.builder.CompareToBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CategoryActivity extends BaseActivity implements AddCategoryFragment.AddListener {
    private RecyclerView.Adapter adapter;
  //  private List<MyList> list;
    @BindView(R.id.rvResults)
    protected RecyclerView rvResults;
    Comparator<CategoryModel> CATEGORY_COMPARATOR = new Comparator<CategoryModel>() {
        @Override
        public int compare(CategoryModel a,CategoryModel b) {
            return new CompareToBuilder().append(a.getName(),b.getName()).toComparison();
        }
    };
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
      //  list = new ArrayList<>();
        ButterKnife.bind(this);
        addToolBar();
        addBackButtonWhite(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CategoryActivity.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
        setToolbarTitle("Category");
        rvResults.setItemAnimator(new DefaultItemAnimator());
        rvResults.addItemDecoration(new SpaceItemDecoration(0, 0, 0, 0));
        rvResults.setHasFixedSize(false);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                itemClick(position);
            }
        }));

        categoriesAdapter = new CategoriesAdapter(getApplicationContext(), CATEGORY_COMPARATOR);
        Collections.sort(CategoryModel.getCategories(), CATEGORY_COMPARATOR);
        categoriesAdapter.edit().add(CategoryModel.getCategories()).commit();
        //CategoryModel model = new CategoryModel();
        // list  = model.getModelAdapter();
        // Current
        //  loadRecyclerViewItem();
         //  adapter = new CustomAdapter(,this);
        rvResults.setAdapter(categoriesAdapter);
        //rvResults
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu1:
              Toast.makeText(this, "Position : " + R.id.menu1, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu2:
                Toast.makeText(this, "Position : " + R.id.menu2, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    private void itemClick(int position) {

      //  Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();

    }






    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(this, "Position : " + R.id.menu1, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu2:
                Toast.makeText(this, "Position : " + R.id.menu2, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    @OnClick(R.id.addCategory)
    public void addCategory(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
        addCategoryFragment.show(fragmentManager, addCategoryFragment.getClass().getSimpleName());
    }

    @Override
    public void onAdd(String text) {
        if (text.isEmpty()) return;
        CategoryModel model = new CategoryModel();
        model.setName(text);
        model.save();
        categoriesAdapter.edit().add(model).commit();
        categoriesAdapter.notifyDataSetChanged();
    }
}
