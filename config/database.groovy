environments {
    development {
        database = 'spring_boot_rest_api_tdd_development'
        url = "jdbc:mysql://localhost/$database"
        user = 'root'
    }

    test {
        database = 'spring_boot_rest_api_tdd_test'
        url = "jdbc:mysql://localhost/$database"
        user = 'root'
    }
}