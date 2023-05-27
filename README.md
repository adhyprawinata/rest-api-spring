Repository ini berisi REST API yang dibangun dengan Spring Boot. API ini memiliki beberapa fitur, termasuk daftar pengguna, autentikasi API key, logging, dan unit test.

API ini mengekspos beberapa endpoint terkait manajemen pengguna:

    GET /api/users: Mengambil daftar semua pengguna.

API Key

Untuk mengotentikasi permintaan yang dilakukan ke API, API key diperlukan.

Logging

API ini menggunakan logging untuk mencatat peristiwa dan informasi penting. Logging membantu dalam debugging, pemantauan performa, dan audit. Pernyataan log dapat ditemukan dalam log aplikasi, yang biasanya disimpan dalam sebuah file.

Unit Test

Unit test telah disertakan untuk memastikan kebenaran dan kehandalan API ini. Test tersebut mencakup berbagai skenario dan endpoint untuk memvalidasi perilaku aplikasi.
