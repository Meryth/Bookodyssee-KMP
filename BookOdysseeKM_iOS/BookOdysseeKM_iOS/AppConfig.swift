//
//  AppConfig.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

let Config = AppConfig.prod

struct AppConfig {
    let userSDK: UserSDK
}

extension AppConfig {
    static var prod: AppConfig {
        .init(
            userSDK: UserSDK(databaseDriverFactory: DatabaseDriverFactory())
        )
    }
}
