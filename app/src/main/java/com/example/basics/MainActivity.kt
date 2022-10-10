package com.example.basics

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.basics.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        var counter = 0
        val textStorage: ArrayDeque<String> = ArrayDeque()

        binding.buttonSubmit.setOnClickListener {
            if (binding.edittextMain.text.isNotEmpty()) {
                counter++
                val textToShow = "$counter. ${binding.edittextMain.text}"
                textStorage.addLast(textToShow)
                binding.textviewMain.text = textToShow
                binding.edittextMain.text.clear()
            } else {
                Snackbar.make(it, "Please insert some text", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        binding.buttonReset.setOnClickListener {
            counter = 0
            binding.textviewMain.text = ""
            textStorage.clear()
        }

        binding.buttonUndo.setOnClickListener {
            if (textStorage.isNotEmpty()) {
                var startIndex = counter.toString().length
                val textToRevert = textStorage.removeLast().substring(++startIndex).trim()
                binding.textviewMain.text = ""
                binding.edittextMain.text = Editable.Factory.getInstance().newEditable(textToRevert)
                counter--
            } else {
                Snackbar.make(it, "There is nothing to revert", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}