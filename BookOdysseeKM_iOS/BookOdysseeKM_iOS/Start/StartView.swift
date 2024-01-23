//
//  StartView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 20.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI
import AsyncReactor

struct StartView: View {
    
//    @Environment(\.managedObjectContext) private var viewContext
    
    var body: some View {
        NavigationStack {
            VStack() {
                Text("Welcome to\nBookOdyssee!")
                    .font(.largeTitle)
                    .foregroundStyle(Color("Secondary"))
                    .padding(.bottom)
                    .multilineTextAlignment(.center)
                
                
                Image("Logo")
                    .resizable()
                    .frame(width: 250, height: 130)
                    .scaledToFit()
                    .padding()
                
                Text("Your pocket library to go")
                    .font(.caption)
                    .foregroundStyle(Color("Secondary"))
                
                
                Group{
                    NavigationLink(
                        destination: ReactorView(LoginReactor()) {
                            LoginView()
                        }
                    ) {
                        Text("Login").padding(15).buttonStyle(.borderless)
                            .frame(maxWidth: .infinity)
                            .background(Color("Primary"))
                            .foregroundColor(.white)
                            .padding(.vertical)
                    }
                }
                
                
                NavigationLink(
                    destination: ReactorView(SearchReactor()) {
                        SearchView()
                    }
                ) {
                    Text("Register")
                        .padding(15)
                        .buttonStyle(.borderless)
                        .frame(maxWidth: .infinity)
                        .background(Color("Primary"))
                        .foregroundColor(.white)
                }
                
            }
            .frame(maxWidth: .infinity)
            .padding(.horizontal)
        }
    }
}

#Preview {
    StartView()
}
