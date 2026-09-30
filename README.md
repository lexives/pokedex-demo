# Pokédex Demo App
A demo app for the Edward Jones interview process. Displays a list of Pokémon data.

## The Prompt
Build a small Android app that fetches a list of Pokémon from the free
PokéAPI ( https://pokeapi.co ) and displays them.

**The one hard requirement:** the app must retrieve data from the network
and show a list of Pokémon. Everything else: architecture, libraries,
language choices, scope, and polish, is up to you. Keep it simple.

**Endpoint to get you started:** GET https://pokeapi.co/api/v2/pokemon?limit=20&amp;offset=0 

Each result includes a name and a url; hitting that url returns full detail
(sprites/images, types, stats, etc.). No API key required.

LLM tools are allowed. Use whatever you'd use in your normal workflow.

**Timebox:** aim for ~3-4 hours. Don't gold-plate. If you trade something off to
save time, feel free to note it.

**Deliverable:** push your project to a GitHub repository (public or private)
and share the link. If private, add the reviewers we specify as collaborators
so we can pull and run it. Include a short README covering how to run it,
the choices you made and why, and what you'd do next with more time.

## What I Did

- Researched best target and minimum SDKs: https://developer.android.com/google/play/requirements/target-sdk
- Researched UI examples of other pokédex apps (google images) to get quick design inspiration.
- Looked through https://pokeapi.co docs and tested hitting some endpoints in insomnia.
- Set up project with a modularized structure and Hilt, Moshi, and Retrofit libraries.
  - Used AI (Gemini in Android Studio) to research best practices around modularization and Hilt setup

### Architectural Decisions
- Minimum SDK will be API 34, due to this requirement from Google:
    - > "New apps and app updates must target Android 16 (API level 36) or higher to be submitted to
      > Google Play; except for Wear OS and Android Automotive OS apps, which must target Android 15
      > (API level 35) or higher, and Android TV and Android XR apps, which must target Android 14
      > (API level 34) or higher."
    - I intend to expand upon this demo app for my personal use, so I would like to target as many
      device types as I can.
- Using a modularized app structure to mimic a real-world application.
- Using dependencies Hilt, Moshi, Retrofit, and Coil.
  - Though not _strictly_ necessary, these are the libraries I'm familiar with, they provide much
  needed functionality, and they are lightweight enough for a small project like this.

### Cuts for Time
- An assumption is made that when we make the api call to `/pokemon`, we will always get as many
results as we requested (i.e. if `limit=20` we will get 20 results). Normally I wouldn't assume that
any response from an api is what we expect it to be, but this assumption cuts down on a lot of 
logic.
- Unit tests for PokemonRepository. This would require setup to inject a mock service and
some more fiddling with Gradle dependencies.
- UI tests completely, since there's not a whole lot to test anyway.
- Style definition with consistent colors, text styles, padding values, etc.
- Outlined text within the type chips
- Dedicated talkback support using mergeDescendants
