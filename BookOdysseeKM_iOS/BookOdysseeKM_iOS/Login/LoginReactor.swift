//
//  LoginReactor.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 20.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import AsyncReactor
import shared

public extension Notification.Name {
    static let DidLogin = Notification.Name("login")
    static let DidRegister = Notification.Name("register")
}

class LoginReactor: AsyncReactor {
    enum Action {
        case onLoginClick
    }
    
    enum SyncAction {
        case setUsername(String)
        case setPassword(String)
    }
    
    struct State {
        var username: String = ""
        var password: String = ""
        var shouldNavigate: Bool = false
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
        }
    }
    
    func action(_ action: Action) async {
        switch action {
        case .onLoginClick:
            do {
                let savedUser = try bookOdysseeSDK.getUser(username: state.username)
                
                if savedUser == nil {
                    print("Error: user not found!")
                    return
                } else {
                    if let userId = savedUser?.id {
                        settings.putLong(key: "userId", value: userId)                    }
                    NotificationCenter.default.post(name: .DidLogin, object: nil)
                }
            } catch {
                print("Invalid credentials!")
                print(error)
            }
        }
    }
}

