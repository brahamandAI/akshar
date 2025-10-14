# Alternative Theme Fix (If you don't want Material Components dependency)

## Option 2: Change Theme Parent

Instead of adding Material Components dependency, you can change the theme parent:

### Current (causing error):
```xml
<style name="Theme.AksharMessaging" parent="Theme.MaterialComponents.DayNight.NoActionBar">
```

### Alternative 1 - Use AppCompat:
```xml
<style name="Theme.AksharMessaging" parent="Theme.AppCompat.DayNight.NoActionBar">
```

### Alternative 2 - Use Material 3:
```xml
<style name="Theme.AksharMessaging" parent="android:Theme.Material.DayNight.NoActionBar">
```

### Alternative 3 - Use basic Android theme:
```xml
<style name="Theme.AksharMessaging" parent="android:Theme.Material.Light.NoActionBar">
```

## Recommendation: Use Option 1 (Add dependency)
Because Material Components provides better theming and component support.
