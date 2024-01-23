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
    
    //    var moc: NSManagedObjectContext
    //    let defaults = UserDefaults.standard
    
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
    
    private let userSDK: UserSDK
    
    @MainActor
    init(
        state: State = State(),
        userSDK: UserSDK = Config.userSDK
    ) {
        self.state = state
        self.userSDK = userSDK
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
            
            let savedUser = userSDK.getUser(username: state.username)
            
            if savedUser == nil {
                userSDK.createUser(username: state.username, password: state.password)
            } else {
                print("User already exists!")
            }
        }
    }
}
