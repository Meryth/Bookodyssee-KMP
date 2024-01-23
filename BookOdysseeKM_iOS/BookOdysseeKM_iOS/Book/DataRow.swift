//
//  DataRow.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DataRow: View {
    
    var label: String
    var value: String
    
    var body: some View {
        HStack() {
            Text(label)
            Spacer()
            Text(value)
        }.padding([.horizontal, .bottom])
    }
}

#Preview {
    DataRow(
        label: "Author",
        value: "John Doe"
    )
}
