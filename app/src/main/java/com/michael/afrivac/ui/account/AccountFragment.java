package com.michael.afrivac.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.michael.afrivac.EditAccountInfoActivity;
import com.michael.afrivac.LanguageHelper;
import com.michael.afrivac.R;
import com.michael.afrivac.Util.Helper;
import com.michael.afrivac.WalletPageActivity;
import com.michael.afrivac.ui.findHotel.FindHotelFragment;
import com.michael.afrivac.ui.popular_destination.PopularDestinationFragment;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Helper helper;

    Button editButton;
    TextView gotoLocality, deleteAccount;

    // firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userID;

    // profile widgets
    TextView userCountry, userEmail, userGender, userLanguage, userNumber, username, fullName;
    private TextView myWallet, MyDestination, savedDestinations, inviteFirends;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_account, container, false);
        helper = new Helper();

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        userCountry = root.findViewById(R.id.user_location);
        userEmail = root.findViewById(R.id.edit_email);
        userGender = root.findViewById(R.id.user_gender);
        userLanguage = root.findViewById(R.id.user_language);
        userNumber = root.findViewById(R.id.edit_phone);
        username = root.findViewById(R.id.user_name);
        fullName = root.findViewById(R.id.full_name);
        deleteAccount = root.findViewById(R.id.delete_account);


        myWallet =root.findViewById(R.id.my_wallet_text);
        myWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WalletPageActivity.class));
            }
        });


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        MyDestination =root.findViewById(R.id.my_destination);
        MyDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PopularDestinationFragment();
                ft.replace(R.id.nav_host_fragment, fragment, "PopularDestinationFragment");
                ft.commit();
            }
        });

        savedDestinations =root.findViewById(R.id.saved_destination);
        savedDestinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FindHotelFragment();
                ft.replace(R.id.nav_host_fragment, fragment, "FindHotelFragment");
                ft.commit();
            }
        });

        inviteFirends =root.findViewById(R.id.invite_friends);
        inviteFirends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.toastMessage(getContext(), "we don't yet have playstore downloadable link to start sharing \n thanks");
            }
        });


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog();
                deleteAccountDialog.show(getParentFragmentManager(),"fragment_delete_account");
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String user_name = (String) snapshot.child("username").getValue();
                String user_email = (String) snapshot.child("email").getValue();
                String user_gender = (String) snapshot.child("gender").getValue();
                String user_language = (String) snapshot.child("language").getValue();
                String user_number = (String) snapshot.child("number").getValue();
                String user_country = (String) snapshot.child("country").getValue();

                fullName.setText(user_name);
                if(user_name != null) {
                    username.setText(user_name);
                }
                if(user_email != null) {
                    userEmail.setText(user_email);
                }
                if(user_gender != null) {
                    userGender.setText(user_gender);
                }
                if(user_language != null) {
                    userLanguage.setText(user_language);
                }
                if(user_number != null) {
                    userNumber.setText(user_number);
                }
                if(user_country != null) {
                    userCountry.setText(user_country);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editButton = root.findViewById(R.id.btn_edit2);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.gotoEditAccountActivity(v.getContext());
            }
        });

        gotoLocality = root.findViewById(R.id.place);

        userLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.language_menu, popup.getMenu());
                popup.show();
            }
        });


        return root;
    }
}
