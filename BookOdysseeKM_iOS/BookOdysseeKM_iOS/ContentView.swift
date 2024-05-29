import SwiftUI
import AsyncReactor
import shared

struct ContentView: View {
    
    @State
    private var didLogin = false
    
    @State
    private var didRegister = false
    
    var body: some View {
        ZStack {
            if didLogin {
                BottomBar()
            } else if didRegister {
                ReactorView(
                    LoginReactor()
                ) {
                    LoginView()
                }
            } else {
                StartView()
            }
        }
        .onReceive(NotificationCenter.default.publisher(for: .DidLogin)) { _ in
            didLogin = true
        }
        .onReceive(NotificationCenter.default.publisher(for: .DidRegister)) { _ in
            didRegister = true
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
