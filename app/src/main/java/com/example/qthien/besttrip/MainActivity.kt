package com.example.qthien.besttrip


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.qthien.besttrip.data.Location
import com.example.qthien.besttrip.data.Place
import com.example.qthien.besttrip.data.Taxi
import com.example.qthien.besttrip.data.User
import com.example.qthien.besttrip.presenter.PreFragLogin
import com.example.qthien.besttrip.presenter.PreMapMain
import com.example.qthien.besttrip.view.fragment.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() , IFragLogin , IListenerFragmentToMain ,OnMapReadyCallback ,IMapMain ,NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mMap: GoogleMap

    private var tagLogin_Register = "login_register"
    private var tagMain = "main"
    private var tagMaps = "maps"
    private val tagSearchTaxi: String = "search_taxi"

    lateinit var fragmentMain : FragmentMain
    lateinit var toggle : ActionBarDrawerToggle

    companion object {
        var locationUser : Location? = null
        var locationDes : Location? = null
        var userNow : User? = null
        var taxi : Taxi? = null
    }

    var checkMaps : Boolean = false
    var optionMenu : Menu? = null
    lateinit var arrTaxi : ArrayList<Taxi>
    lateinit var navigationView : NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setDrawerNavigation()
        evenDrawerNavigation()

        edtCurrent.setOnClickListener({
            showFullAddress(edtCurrent.text.toString())
        })

        edtDestination.setOnClickListener({
            if(!edtDestination.text.toString().isEmpty())
                showFullAddress(edtDestination.text.toString())
            else
            {
                val fragmentSelectDestination = FragmentSelectDestination()
                val bundle = Bundle()
                bundle.putString("call" , "edt")
                fragmentSelectDestination.arguments = bundle
                loadOrAddFragment( fragmentSelectDestination, "select_des")
            }
        })

        fragmentMain = FragmentMain()

        if(fragmentMain.isInLayout)
            removeMFragment(tagMaps)
        else
            loadOrAddFragment(fragmentMain , tagMain)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Taxi")
        addTaxi()

        myRef.setValue(arrTaxi)
     }

    fun addTaxi(){
        val arrTaxiCatelogy = mapOf(
            1 to "4 Seaters Economy Taxi",
            2 to "7 Seaters Economy Taxi",
            3 to "Premium Taxi's")

        arrTaxi = ArrayList()
        arrTaxi.add(Taxi("VINATAXI","5000" , "4500" , "0122-3344" , "" , 1 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("VINASUN","6000" , "5500" , "0133-4455" , "0123-4455" , 1 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("XINNHAT","5500" , "4000" , "0144-5566" , "" , 1 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("MAILINH","7000" , "6000" , "0155-6677" , "" , 1 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("RYDER","10000" , "10000" , "0166-7788" , "" , 3 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("HOWARD","7000" , "5000" , "0177-8899" , "" , 3 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("ALAN","7500" , "7000" , "0188-9910" , "0198-9910" , 3 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("JANE","6500" , "5500" , "0199-1011" , "" , 3 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("WHISS","4000" , "3500" , "0110-1112" , "" , 2 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("SARA","5000" , "4000" , "0112-1314" , "0122-1314" , 2 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("HARRY","6000" , "5500" , "0113-1415" , "" , 2 , "https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
        arrTaxi.add(Taxi("XUNNHUN","8000" , "6000" , "0114-1516" , "" , 2 ,"https://img.freepik.com/free-vector/classic-taxi-logo-design_1344-123.jpg?size=338&ext=jpg"))
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        Log.d("aaaaaa" , "dodododod")
        return super.onSupportNavigateUp()
    }

    fun showFullAddress(address : String){
        val bundle = Bundle()
        bundle.putString("address" , address)
        val fragmentShow = FragmentDialogAddress()
        fragmentShow.arguments = bundle
        fragmentShow.show(supportFragmentManager , "FragShow")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main , menu)
        menu?.findItem(R.id.menu_map)?.isVisible = false
        menu?.findItem(R.id.menu_call)?.isVisible = false
        menu?.findItem(R.id.menu_searchTaxi)?.isVisible = false
        optionMenu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_map) {
            if(!checkMaps){
                item.setIcon(R.drawable.ic_map_on)
                loadMaps()
                checkMaps = true
                setTitle("Map")
            }else{
                onBackPressed()
            }
        }

        if(item?.itemId == R.id.menu_call) {
            if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.CALL_PHONE)
                                                                == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + taxi?.phone1?.replace("-" , "")));
                startActivity(intent)
            }
            else
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE) , 123)
                }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + taxi?.phone1?.replace("-" , "")));
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Permission deny" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadOrAddFragment(fragment : Fragment , tag : String){
        if(supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction().replace(R.id.frmMain2 , fragment , tag).addToBackStack(tag).commit()
        else
            supportFragmentManager.popBackStack(tag , 0)
    }

    fun evenDrawerNavigation(){
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val sharedPreferences = getSharedPreferences("Login" , Activity.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", null)
        loadNoUser()

        if(id != null)
        {
            PreFragLogin(this).getUser(id)
        }
    }

    override fun success(user: User?) {
        userNow = user
        loadHasUser()
    }

    fun loadNoUser(){
        val headerView = navigationView.getHeaderView(0)
        val menuLogout = navigationView.menu.findItem(R.id.nav_logout)
        val menuHome = navigationView.menu.findItem(R.id.nav_home)
        menuHome.isChecked = true
        menuLogout.isVisible = false
        headerView.txtNameDrawer.setText(R.string.login)
        headerView.txtNameDrawer.setOnClickListener({
            loadOrAddFragment(FragmentLogin_Register(), tagLogin_Register)
            drawer_layout.closeDrawer(GravityCompat.START , false)
            menuHome.isChecked = false
        })
        headerView.txtEmailDrawer.setText(R.string.wellcome)
    }

    fun loadHasUser(){
        val headerView = navigationView.getHeaderView(0)
        val menuLogout = navigationView.menu.findItem(R.id.nav_logout)
        menuLogout.isVisible = true
        headerView.txtNameDrawer.setText(userNow?.username)
        headerView.txtNameDrawer.setOnClickListener({

        })
        headerView.txtEmailDrawer.setText(userNow?.email)
        supportFragmentManager.popBackStack(tagMain , 0)
    }

    private fun setDrawerNavigation(){
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            if(supportFragmentManager.backStackEntryCount <= 1) {
                finish()
            }
            else{
                if(supportFragmentManager.findFragmentByTag(tagMaps) != null){
                    removeMFragment(tagMaps)
                    optionMenu?.findItem(R.id.menu_map)?.setIcon(R.drawable.ic_map_off)
                    checkMaps = false
                    super.onBackPressed()
                }
                else
                    supportFragmentManager.popBackStack()
            }

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.popBackStack(tagMain , 0)
                removeMFragment(tagMaps)
                optionMenu?.findItem(R.id.menu_map)?.setIcon(R.drawable.ic_map_off)?.isVisible = false
                optionMenu?.findItem(R.id.menu_call)?.isVisible = false
                checkMaps = false
            }
            R.id.nav_charges -> {
                loadOrAddFragment(FragmentSearchTaxi() , tagSearchTaxi)
            }
            R.id.nav_logout -> {
                userNow = null
                loadNoUser()

                getSharedPreferences("Login" , Context.MODE_PRIVATE).edit().clear().apply()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun loadMaps(){
        PreMapMain(this).getListStep(mapOf("origin" to "${locationUser?.lat},${locationUser?.lng}" , "destination" to "${locationDes?.lat},${locationDes?.lng}"))
        val mapFragment = SupportMapFragment.newInstance()
        loadOrAddFragment(mapFragment , tagMaps)
        mapFragment.getMapAsync(this)
    }

    fun removeMFragment(tag : String){
        val f = supportFragmentManager.findFragmentByTag(tag)
        if(f != null)
            supportFragmentManager.beginTransaction().remove(f).commit()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera

        val from = LatLng(locationUser?.lat!! , locationUser?.lng!!)
        val to = LatLng(locationDes?.lat!! , locationDes?.lng!! )
        val place1 = MarkerOptions().position(from).title("From")
        val place2 = MarkerOptions().position(to).title("To")

        mMap.addMarker(place1)
        mMap.addMarker(place2)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney , 13.toFloat()))
        val cameraPosition = CameraPosition.Builder()
            .target(
                from
            )      // Sets the center of the map to location user
            .zoom(13f)                   // Sets the zoom
            .bearing(60f)                // Sets the orientation of the camera to east
            .tilt(70f)                   // Sets the tilt of the camera to 30 degrees
            .build()                   // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

//                mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        Log.d("Vaoooooo" , "Vào khoongg ?")
    }

    // callbackIMapMan
    override fun success(arrResult: ArrayList<LatLng>, arrPolyline: ArrayList<String>) {

        Log.d("Sizee" , arrPolyline.size.toString())
        for(p in arrPolyline){
            val po = PolylineOptions()
            po.addAll(PolyUtil.decode(p))
            po.width(8f)
            po.color(Color.parseColor("#1e9793"))

            mMap.addPolyline(po)
        }
//        val polyLineOptions = PolylineOptions()
//        Log.d("Sizee" , arrResult.size.toString())
//        polyLineOptions.addAll(arrResult)
//
//        Log.d("Sizee" , polyLineOptions.toString())
//        val line = mMap.addPolyline(polyLineOptions)
//        line.color = Color.BLUE
//        line.width = 5f
    }

    override fun failure(message: String) {
        Toast.makeText(this , message , Toast.LENGTH_SHORT).show()
    }

    override fun setVisibilityLinearEdt(b : Boolean , nameAddress : String) {
        linearLayout.visibility = when(b){
            true -> View.VISIBLE
            else -> View.GONE
        }
        edtCurrent.setText(fragmentMain.address)
        if(edtDestination.text.isEmpty())
            edtDestination.setText(nameAddress)
        if(locationDes == null)
            edtDestination.setText("")
    }

    override fun setTitleMain(title: String) {
        setTitle(title)
    }

    override fun setVisibilityDrawerButton(b: Boolean) {
        toggle.setDrawerIndicatorEnabled(b)

        if(b){
           drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
       }
       else{
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setToolbarNavigationClickListener {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(it.windowToken , 0)
                supportFragmentManager.popBackStack()
            }
//        toolbar.setNavigationOnClickListener({
//            onBackPressed()
//        })
       }
    }

    override fun setVisibilityIconMap(b: Boolean) {
        if(optionMenu != null)
            optionMenu?.findItem(R.id.menu_map)?.isVisible = b
    }

    override fun setVisibilityIconCall(b: Boolean) {
        if(optionMenu != null)
             optionMenu?.findItem(R.id.menu_call)?.isVisible = b
    }

    override fun setCheckedNavDrawer(id: Int) {
        navigationView.setCheckedItem(id)
    }

    override fun setLoginSuccess(user: User) {
        userNow = user
        loadHasUser()
    }

}
