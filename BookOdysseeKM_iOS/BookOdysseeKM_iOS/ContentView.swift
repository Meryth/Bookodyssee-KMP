import SwiftUI
import AsyncReactor
import shared

struct ContentView: View {
    
    @State
    private var didLogin = false
    
    var body: some View {
        ZStack {
            if didLogin {
                BottomBar()
            } else {
                StartView()
            }
        }
        .onReceive(NotificationCenter.default.publisher(for: .DidLogin)) { _ in
            didLogin = true
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
