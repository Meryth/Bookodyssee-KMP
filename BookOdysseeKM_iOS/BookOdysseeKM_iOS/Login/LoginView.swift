//
//  LoginView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 20.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    
    @EnvironmentObject
    private var reactor: LoginReactor
    
//    @Environment(\.managedObjectContext) private var viewContext
    
    var body: some View {
        NavigationStack{
            VStack(alignment: .center) {
                TextField(
                    "Username",
                    text: Binding(
                        get: {reactor.username},
                        set: {reactor.action(.setUsername($0))}
                    )
                )
                .padding(.bottom)
                .textFieldStyle(.roundedBorder)
                .textInputAutocapitalization(.never)
                
                SecureField(
                    "Password",
                    text: Binding(
                        get: {reactor.password},
                        set: {reactor.action(.setPassword($0))}
                    )
                )
                .padding(.bottom)
                .textFieldStyle(.roundedBorder)
                .textInputAutocapitalization(.never)
                
                Button(action: {
                    reactor.send(.onLoginClick)
                }) {
                    Text("Login")
                        .padding(15)
                        .frame(maxWidth: .infinity)
                        .background(Color("Primary"))
                        .foregroundColor(.white)
                }
            }
            .padding(.horizontal)
        }
    }
}

#Preview {
    LoginView()
}
