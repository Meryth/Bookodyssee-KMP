//
//  BottomBar.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 24.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor

struct BottomBar: View {
    
    var body: some View {
        TabView {
            ReactorView(HomeReactor()) {
                HomeView()
                    .tabItem {
                        Label("Home", systemImage: "book")
                    }
            }
            ReactorView(FinishedReactor()) {
                FinishedView()
                    .tabItem{
                        Label("Finished", systemImage: "book.closed")
                    }
            }
        }
    }
}

#Preview {
    BottomBar()
}
