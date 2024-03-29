package com.uotttawa.lschu105.gcccyclingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePage extends AppCompatActivity {
    private ImageButton roundButton;
    private Button createEventsButton;
    private Button viewEventsButton;
    private Button editEventTypesButton;
    private Button editUsersButton;
    private TextView textView;
    private TextView userName;
    private TextView Welcome;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        // Your existing code
        roundButton = findViewById(R.id.roundButton);
        createEventsButton = findViewById(R.id.button1);
        viewEventsButton = findViewById(R.id.button2);
        editEventTypesButton = findViewById(R.id.button3);
        editUsersButton = findViewById(R.id.button4);
        userName = findViewById(R.id.userName);
        TextView cardTitleBold = findViewById(R.id.cardTitleBold);
        TextView cardSubtitle = findViewById(R.id.cardSubtitle);

        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Set up navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_logout) {
                    SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    // Redirect to login
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.nav_profile) {
                    SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    String username = preferences.getString("username", "");
                    System.out.println(username);
                    Intent intent = new Intent(getApplicationContext(), ProfileView.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(WelcomePage.this,"Not implemented",Toast.LENGTH_SHORT);
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });

        // Set up click listener for the menu button
        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String savedUsername = preferences.getString("username", "");
        String savedRole = preferences.getString("role", "");
        userName.setText(savedUsername);

        // Retrieve profile information from Firebase


        // Update text based on user role
        switch (savedRole) {
            case "Admin":
                cardTitleBold.setText("Create new Event");
                cardSubtitle.setText("Admin");
                break;
            case "CyclingClub":
                cardTitleBold.setText("Create new Event");
                cardSubtitle.setText("Organize Cycling events");
                break;
            case "Participant":
                cardTitleBold.setText("Join an Event");
                cardSubtitle.setText("Find an event happening near you");
                break;
        }
        updateButtonVisibility(savedRole);
    }
    public void onNear(View view){
        Intent intent = new Intent(getApplicationContext(), EventFeed.class);
        startActivity(intent);
        finish();
    }

    private void updateButtonVisibility(String userRole) {
        createEventsButton.setVisibility(View.INVISIBLE);
        viewEventsButton.setVisibility(View.INVISIBLE);
        editEventTypesButton.setVisibility(View.INVISIBLE);
        editUsersButton.setVisibility(View.INVISIBLE);
        createEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventCreation.class);
                startActivity(intent);
                finish();
            }
        });
        roundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventCreation.class);
                startActivity(intent);
                finish();
            }
        });

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity for viewing events (replace with the correct activity)
                Intent intent = new Intent(getApplicationContext(), EventManagement.class);
                startActivity(intent);
                finish();
            }
        });
        editEventTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity for editing event types (replace with the correct activity)
                Intent intent = new Intent(getApplicationContext(), EventTypesManagement.class);
                startActivity(intent);
                finish();
            }
        });

        editUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the activity for editing users (replace with the correct activity)
                Intent intent = new Intent(getApplicationContext(), AccountManagement.class);
                startActivity(intent);
                finish();
            }
        });
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String savedUsername = preferences.getString("username", "");
        String savedRole = preferences.getString("role", "");
        userName.setText(savedUsername);

        // Update visibility based on user role
        switch (userRole) {
            case "Admin":
                createEventsButton.setVisibility(View.VISIBLE);
                viewEventsButton.setVisibility(View.VISIBLE);
                editEventTypesButton.setVisibility(View.VISIBLE);
                editUsersButton.setVisibility(View.VISIBLE);
                break;
            case "CyclingClub":
                createEventsButton.setVisibility(View.VISIBLE);
                viewEventsButton.setVisibility(View.VISIBLE);
                editEventTypesButton.setVisibility(View.VISIBLE);
                editUsersButton.setVisibility(View.VISIBLE);
                editEventTypesButton.setText("Participants");
                editUsersButton.setText("Feedback");

                Welcome = findViewById(R.id.userName);
                Welcome.setOnClickListener(v -> {
                    String username = preferences.getString("username", "");
                    Intent intent = new Intent(getApplicationContext(), ProfileView.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                });
                DatabaseReference profileRefs = FirebaseDatabase.getInstance().getReference("Profile").child(savedUsername);
                profileRefs.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Convert DataSnapshot to Profile object
                            Profile profile = dataSnapshot.getValue(Profile.class);

                            // Validate profile data
                            if (profile != null) {
                                if (profile.getSocialMediaLinks() == null || profile.getPhoneNumber() == null || profile.getLocation() == null) {
                                    Intent intent = new Intent(getApplicationContext(), ProfileSettings.class);
                                    intent.putExtra("username", savedUsername);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Handle the case where the profile is null
                            }
                        } else {
                            // If the profile node does not exist, redirect to ProfileSettings
                            Intent intent = new Intent(getApplicationContext(), ProfileSettings.class);
                            intent.putExtra("username", savedUsername);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors if needed
                    }
                });
                editEventTypesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing event types (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), FriendsPage.class);
                        startActivity(intent);
                        finish();
                    }
                });
                editUsersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing event types (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), ClubFeedback.class);
                        startActivity(intent);
                        finish();
                    }
                });

                break;
            case "Participant":
                createEventsButton.setVisibility(View.VISIBLE);
                viewEventsButton.setVisibility(View.VISIBLE);
                editEventTypesButton.setVisibility(View.VISIBLE);
                editUsersButton.setVisibility(View.VISIBLE);

                viewEventsButton.setText("Find Club");
                createEventsButton.setText("Manage Events");
                editEventTypesButton.setText("Friends");
                editUsersButton.setText("Rate Club");
                viewEventsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing users (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), ClubFeed.class);
                        startActivity(intent);
                        finish();
                    }
                });
                createEventsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing users (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), ParticipantEventManagement.class);
                        startActivity(intent);
                        finish();
                    }
                });
                editEventTypesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing event types (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), FriendsPage.class);
                        startActivity(intent);
                        finish();
                    }
                });
                editUsersButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the activity for editing event types (replace with the correct activity)
                        Intent intent = new Intent(getApplicationContext(), ClubFeedback.class);
                        startActivity(intent);
                        finish();
                    }
                });


        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), WelcomePage.class);
        startActivity(intent);
        finish();
    }

}