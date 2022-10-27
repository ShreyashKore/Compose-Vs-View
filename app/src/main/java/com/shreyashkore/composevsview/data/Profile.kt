package com.shreyashkore.composevsview.data

import io.bloco.faker.Faker
import io.bloco.faker.components.Address


data class Profile(
    val name: String,
    val image: String,
    val company: String,
    val address: String,
    val phone: String,
    val companyImage: String,
)

fun Address.fullAddress() : String = streetAddress() + " " + buildingNumber() + " " + postcode()

val faker = Faker()
val FAKE_PROFILES = (0..1000).map {
    Profile(
        name = faker.name.name(),
        image = "https://i.pravatar.cc/350?u=${faker.phoneNumber.phoneNumber()}",
        company = faker.company.name(),
        address = faker.address.fullAddress(),
        phone = faker.phoneNumber.phoneNumber(),
        companyImage = faker.company.logo()
    )
}

