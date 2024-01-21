import SwiftUI
import AsyncReactor
import shared

struct ContentView: View {
	//let greet = Greeting().greet()

	var body: some View {
        ReactorView(SearchReactor()) {
            SearchView()
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
