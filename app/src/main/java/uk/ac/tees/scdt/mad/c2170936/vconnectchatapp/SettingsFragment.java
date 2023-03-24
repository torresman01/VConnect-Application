package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private TextView profileName, profilePhoneNumber;
    private CircleImageView imageViewCircleProfile;
    private Button loadProfilePage, loadChangePassword, loadDeleteAccount, acctSignOut;
    AlertDialog.Builder alert;


    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        profileName = view.findViewById(R.id.profileName);
        profilePhoneNumber = view.findViewById(R.id.profilePhone);
        imageViewCircleProfile = view.findViewById(R.id.imageViewCircleProfile);
        loadProfilePage = view.findViewById(R.id.editBtn);
        loadChangePassword = view.findViewById(R.id.changePasswordBtn);
        loadDeleteAccount = view.findViewById(R.id.deleteAccountBtn);
        acctSignOut = view.findViewById(R.id.signOutBtn);
        alert = new AlertDialog.Builder(getContext());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseUser = auth.getCurrentUser();
        storage = FirebaseStorage.getInstance();



        loadProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),ProfileActivity.class);
                startActivity(intent);

            }
        });

        loadChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),ChangePasswordActivity.class);
                startActivity(intent);

            }
        });

        loadDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteUserDetails();

            }
        });

        acctSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Sign Out Successful", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getContext(),loginActivity.class);
                startActivity(intent);

            }
        });


        getUserDetails();

        return view;
    }

    public void getUserDetails()
    {
        reference.child("User").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String image = snapshot.child("image").getValue().toString();

                profileName.setText(name);
                profilePhoneNumber.setText(phone);


                if (image.equals("null"))
                {
                    imageViewCircleProfile.setImageResource(R.drawable.ic_account);
                }
                else
                {
                    Picasso.get().load(image).into(imageViewCircleProfile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void deleteUserDetails()
    {
        alert.setTitle("Delete Account Permanently?")
                .setMessage("Are You Sure?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        DatabaseReference DUser = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
//                        DUser.removeValue(); // this will empty the database tree

                        //This will delete the authentication details
                        firebaseUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(getContext(), "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                startActivity(new Intent(getContext(),loginActivity.class));
                                getActivity().finish();

                            }
                        });

                    }
                }).setNegativeButton("Cancel", null)
                .create().show();
    }
}