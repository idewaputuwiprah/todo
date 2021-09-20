# README #

Project ini adalah kerangka aplikasi **Todo List** dengan fitur sebagai berikut:


1. Melakukan penyimpanan todo list dengan geo tagging.
2. Reminder (pengigat) todo list.

### Langkah pengerjaan ###
1. Clone repository ini
2. Kerjakan soal-soal dibawah
3. Buat branch anda dan lakukan commit pada branch buatan anda sendiri
4. Push branch anda ke repository ini

### Requirement ###
1. Gunakan android jetpack.
2. Gunakan DI(Dependency Injection), Dagger atau Hilt.
3. Gunakan dataBinding untuk menampilkan data.
4. Gunakan room untuk menyimpan data di lokal device.

## Soal ##
1. Tambahkan fitur reminder untuk todo list yang ada.
2. Kerjakan task-task yang ada pada project ini. task dapat ditemukan melalui panel TODO di **Android Studio**,
    yang terletak dibagian kiri bawah. (sebagian task memiliki unit-testing).

    Task:
    ** com.example.todo.data.todo.TodoLocalRepositoryImpl **


        TODO("Lakukan penyimpanan data todo").


        TODO("Lakukan pengambilan data todo berdasarkan ID").


        TODO("Lakukan pengambilan list data todo").


    ** com.example.todo.database.dao.TodoDao **


        TODO("Buatlah query yang dibutuhkan oleh reporsitory").


    ** com.example.todo.presentation.main.fragment.create.TodoCreateFragment **


        TODO ("Lakukan initialized pada value dibawah ini").


        TODO("Buatlah viewBinding yang diperlukan oleh fragment").


        TODO("Buatlah marker untuk menampilkan koordinat todo pada maps,"
                    "Marker yang tampil di maps hanya boleh satu").


        TODO("Buatlah interaksi user dengan map, ketika user melakukan longpress."
                    "Buatlah sebuah marker berdasarkan koordinat yang user tekan dan marker di maps hanya boleh satu"). (2.5)


    ** com.example.todo.presentation.main.fragment.todo.TodoListFragment **


        TODO ("Lakukan initialized pada value dibawah ini").


        TODO("Buatlah viewBinding yang diperlukan oleh fragment").


    ** com.example.todo.presentation.main.MainViewModel **


        TODO("Daptkan list todo dan assign value listTodo ke liveData yang disediakan").


        TODO("Ketika object todo berhasil didapatkan,"
                    "lakukan assign value ke masing-masing liveData yang disediakan").


        TODO("Lakukan penyimpanan todo dan ketika perhasil melakukan penyimpanan,"
                    "perbaharui data pada list").


        TODO("Panggil DatePickerDialog untuk medapatkan tahun, bulan, dan tanggal."
                    "kemudian lakukan pemanggilan method getHours()").


        TODO("Panggil TimePickerDialog untuk medapatkan jam dan menit."
                    "Kemudian update tampilan.").


    ** com.example.todo.utils.DialogUtils **


        TODO("Muncul sebuah dialog dengan ketentuan dibawah ini, "
                        "gunakan R.layout_error_view untuk dialog view"
                        "StringEmptyException sebagai R.string.title_cannot_empty,"
                        "LatLngException sebagai R.string.lat_lng_cannot_zero,"
                        "FormatException sebagai R.string.format_date_is_not_correct"
                        "else sebagai R.string.unknown").


    ** com.example.todo.IViewModelFactory **


        TODO("Buatlah factory untuk membuat viewmodel jika dibutuhkan").


    ** com.example.todo.domain.impl.SaveTodoImpl **


        TODO("Buatlah logic untuk melakukan save todo,"
                    "Code dibuat berdasarkan unit test yang telah disediakan").


    ** com.example.todo.domain.impl.GetListImpl **


        TODO("Buatlah logic untuk mendapatkan list todo."
                "Code dibuat berdasarkan unit test yang telah disediakan").


    ** com.example.todo.domain.impl.GetTodoByIdImpl **
  
    
        TODO("Buatlah logic untuk mengambil data todo by id."
                "Code dibuat berdasarkan unit test yang telah disediakan").


3. Jelaskan perbedaan dari MVP dan MVVM, berdasarkan pemahaman anda **Tanpa Browsing**
4. Jelaskan perbedaan dari Activity, Fragment, dan Background Service, berasarkan pemahaman anda **Tanpa Browsing**
    
    

