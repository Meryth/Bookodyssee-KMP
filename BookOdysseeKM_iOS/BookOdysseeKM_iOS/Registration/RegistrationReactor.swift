//
//  RegistrationReactor.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 22.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import AsyncReactor
import shared

class RegistrationReactor: AsyncReactor {
    
    enum Action {
        case onRegisterClick
    }
    
    enum SyncAction {
        case setUsername(String)
        case setPassword(String)
        case setConfirmPassword(String)
    }
    
    struct State {
        var username: String = ""
        var password: String = ""
        var confirmPassword: String = ""
    }
    
    @Published
    private(set) var state: State
    
    private let bookOdysseeSDK: BookOdysseeSDK
    private let settings : Multiplatform_settingsSettings
    
    @MainActor
    init(
        state: State = State(),
        bookOdysseeSDK: BookOdysseeSDK = Config.bookOdysseeSDK,
        settings: Multiplatform_settingsSettings = Config.settings
    ) {
        self.state = state
        self.bookOdysseeSDK = bookOdysseeSDK
        self.settings = settings
    }
    
    func action(_ action: SyncAction) {
        switch action {
        case .setUsername(let username):
            state.username = username
        case .setPassword(let password):
            state.password = password
        case .setConfirmPassword(let confirmPassword):
            state.confirmPassword = confirmPassword
        }
    }
    
    func action(_ action: Action) async {
        switch action {
        case .onRegisterClick:
            do {
                
                let savedUser = try bookOdysseeSDK.getUser(username: state.username)
              
                if savedUser == nil {
                    try bookOdysseeSDK.createUser(username: state.username, password: state.password)
                    
                    let newUser = try bookOdysseeSDK.getUser(username: state.username)
                    
                    guard let userId = newUser?.id else {
                        print("Error: Id cannot be nil!")
                        return
                    }
                    
                    settings.putLong(key: "userId", value: userId)
                    
                    
                } else {
                    print("User already exists!")
                }
                
              
                
            } catch {
                print("Error when trying to register!")
                print(error)
            }
        }
    }
}
