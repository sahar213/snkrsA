package com.sahar.snkrsa.services;

import android.util.Log;

import androidx.annotation.Nullable;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.Order;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/// a service to interact with the Firebase Realtime Database.
/// this class is a singleton, use getInstance() to get an instance of this class
/// @see #getInstance()
/// @see FirebaseDatabase
public class DatabaseService {

    /// tag for logging
    /// @see Log
    private static final String TAG = "DatabaseService";
    private AuthenticationService authenticationService = AuthenticationService.getInstance();

    /// callback interface for database operations
    /// @param <T> the type of the object to return
    /// @see DatabaseCallback#onCompleted(Object)
    /// @see DatabaseCallback#onFailed(Exception)
    public interface DatabaseCallback<T> {
        /// called when the operation is completed successfully
        void onCompleted(T object);

        /// called when the operation fails with an exception
        void onFailed(Exception e);
    }

    /// the instance of this class
    /// @see #getInstance()
    private static DatabaseService instance;

    /// the reference to the database
    /// @see DatabaseReference
    /// @see FirebaseDatabase#getReference()
    private final DatabaseReference databaseReference;

    /// use getInstance() to get an instance of this class
    /// @see DatabaseService#getInstance()
    private DatabaseService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    /// get an instance of this class
    /// @return an instance of this class
    /// @see DatabaseService
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    // private generic methods to write and read data from the database

    /// write data to the database at a specific path
    /// @param path the path to write the data to
    /// @param data the data to write (can be any object, but must be serializable, i.e. must have a default constructor and all fields must have getters and setters)
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
    private void writeData(@NotNull final String path, @NotNull final Object data, final @Nullable DatabaseCallback<Void> callback) {
        databaseReference.child(path).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (callback == null) return;
                callback.onCompleted(task.getResult());
            } else {
                if (callback == null) return;
                callback.onFailed(task.getException());
            }
        });
    }

    /// read data from the database at a specific path
    /// @param path the path to read the data from
    /// @return a DatabaseReference object to read the data from
    /// @see DatabaseReference

    private DatabaseReference readData(@NotNull final String path) {
        return databaseReference.child(path);
    }


    /// get data from the database at a specific path
    /// @param path the path to get the data from
    /// @param clazz the class of the object to return
    /// @param callback the callback to call when the operation is completed
    /// @return void
    /// @see DatabaseCallback
    /// @see Class
    private <T> void getData(@NotNull final String path, @NotNull final Class<T> clazz, @NotNull final DatabaseCallback<T> callback) {
        readData(path).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            T data = task.getResult().getValue(clazz);
            callback.onCompleted(data);
        });
    }
    /// generate a new id for a new object in the database
    /// @param path the path to generate the id for
    /// @return a new id for the object
    /// @see String
    /// @see DatabaseReference#push()

    private String generateNewId(@NotNull final String path) {
        return databaseReference.child(path).push().getKey();
    }

    // end of private methods for reading and writing data

    // public methods to interact with the database

    /// create a new user in the database
    /// @param user the user object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User



    public void createNewUser(@NotNull final User user, @Nullable final DatabaseCallback<Void> callback) {
        writeData("Users/" + user.getId(), user, callback);
    }



    /// create a new item in the database
    /// @param item the item object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Item
    public void createNewProduct(@NotNull final Product product, @Nullable final DatabaseCallback<Void> callback) {
        writeData("Products/" + product.getId(), product, callback);
    }





    /// create a new item in the database
    /// @param item the item object to create
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive void
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Item
    public void createNewOreder(@NotNull final Order order,  @Nullable final DatabaseCallback<Void> callback) {
        writeData("Orders/" + order.getOrderId(), order, callback);
        writeData("UserOrders/" +order.getUser().getId()+"/"+ order.getOrderId(), order, callback);

    }


    /// get a user from the database
    /// @param uid the id of the user to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the user object
    ///             if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see User
    public void getUser(@NotNull final String uid, @NotNull final DatabaseCallback<User> callback) {
        getData("Users/" + uid, User.class, callback);
    }



    /// get a item from the database
    /// @param itemId the id of the item to get
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive the item object
    ///              if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Item
    public void getProduct(@NotNull final String productId, @NotNull final DatabaseCallback<Product> callback) {
        getData("Products/" + productId, Product.class, callback);
    }


    /// generate a new id for a new item in the database
    /// @return a new id for the item
    /// @see #generateNewId(String)
    /// @see Item
    public String generateProductId() {
        return generateNewId("Products");
    }


    /// generate a new id for a new item in the database
    /// @return a new id for the item
    /// @see #generateNewId(String)
    /// @see Item
    public String generateOrderId() {
        return generateNewId("Orders");
    }



    /// get all the items from the database
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of item objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Item
    /// @see #getData(String, Class, DatabaseCallback)
    public void getProducts(@NotNull final DatabaseCallback<List<Product>> callback) {
        readData("Products").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<Product> productList = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                Product product = dataSnapshot.getValue(Product.class);
                Log.d(TAG, "Got item: " + product);
                productList.add(product);
            });

            callback.onCompleted(productList);
        });
    }

    /// get all the users from the database
    /// @param callback the callback to call when the operation is completed
    ///              the callback will receive a list of item objects
    ///            if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see List
    /// @see Item
    /// @see #getData(String, Class, DatabaseCallback)
    public void getUsers(@NotNull final DatabaseCallback<List<User>> callback) {
        readData("Users").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error getting data", task.getException());
                callback.onFailed(task.getException());
                return;
            }
            List<User> users = new ArrayList<>();
            task.getResult().getChildren().forEach(dataSnapshot -> {
                User user = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Got user: " + user);
                users.add(user);
            });

            callback.onCompleted(users);
        });
    }

    /// get a cart from the database
    /// @param cartId the id of the cart to get
    /// @param callback the callback to call when the operation is completed
    ///                the callback will receive the cart object
    ///               if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Cart
    public void getCart(String uid, @NotNull final DatabaseCallback<Cart> callback) {
        getData("userCart/" + uid, Cart.class, callback);
    }


    /// create a new cart in the database
    /// @param cart the cart object to create
    /// @param callback the callback to call when the operation is completed
    ///               the callback will receive void
    ///              if the operation fails, the callback will receive an exception
    /// @return void
    /// @see DatabaseCallback
    /// @see Cart
    public void updateCart(@NotNull final Cart cart, String uid, @Nullable final DatabaseCallback<Void> callback) {
        writeData("userCart/" + uid, cart, callback);
    }

    public void updateProduct(@NotNull final Product product, String uid, @Nullable final DatabaseCallback<Void> callback) {
        writeData("Products/" + uid, product, callback);
    }





}
