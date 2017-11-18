package com.github.androidpirate.flipcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.androidpirate.flipcard.R;
import com.github.androidpirate.flipcard.model.Deck;
import com.github.androidpirate.flipcard.utils.DeckManager;

import java.util.ArrayList;

/**
 * Adapter class for deck list.
 */
public class DeckListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CATEGORY = 0;
    private static final int DECK = 1;
    private ArrayList<Deck> mDecks = new ArrayList<>();
    private ArrayList<Object> mItems = new ArrayList<>();
    private OnAdapterInteractionListener mListener;
    private DeckManager mDeckManager;
    private Context mContext;

    public DeckListAdapter(OnAdapterInteractionListener listener, ArrayList<Deck> decks) {
        mListener = listener;
        mDecks = decks;
        mDeckManager = new DeckManager();
        mDeckManager.sortByCategories(mDecks);
        mItems = mDeckManager.getListItems(mDecks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case CATEGORY:
                View categoryView = inflater.inflate(R.layout.deck_list_category_item, parent, false);
                viewHolder = new CategoryHolder(categoryView);
                break;
            case DECK:
                View deckView = inflater.inflate(R.layout.deck_list_item, parent, false);
                viewHolder = new DeckHolder(deckView);
                break;
            default:
                throw new NullPointerException("View holder can not be null.");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CATEGORY:
                String category = (String) mItems.get(position);
                CategoryHolder categoryHolder = (CategoryHolder) holder;
                categoryHolder.onBindCategory(category);
                break;
            case DECK:
                Deck deck = (Deck) mItems.get(position);
                DeckHolder deckHolder = (DeckHolder) holder;
                deckHolder.onBindDeck(deck);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof String) {
            return CATEGORY;
        } else {
            return DECK;
        }
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView mCategory;

        public CategoryHolder(View itemView) {
            super(itemView);
            mCategory = itemView.findViewById(R.id.tv_category);
        }

        public void onBindCategory(String category) {
            mCategory.setText(category);
        }
    }

    class DeckHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mCategory;
        private TextView mSize;

        public DeckHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mCategory = itemView.findViewById(R.id.tv_category);
            mSize = itemView.findViewById(R.id.tv_size);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object item = mItems.get(getAdapterPosition());
                    if(item instanceof Deck) {
                        Deck deck = (Deck) item;
                        mListener.onItemClick(deck);
                    }
                }
            });
        }

        public void onBindDeck(Deck deck) {
            mTitle.setText(deck.getTitle());
            mCategory.setText(deck.getCategory());
            mSize.setText(String.format(mContext.getString(R.string.deck_list_item_size), deck.getSize()));
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClick(Deck deck);
    }
}
