/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import { registerLicense } from '@syncfusion/ej2-base';
import { syncFusion } from './app/enviroments/environment';

// Registering Syncfusion license key
registerLicense(syncFusion.licensekey);

platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
})
  .catch(err => console.error(err));
