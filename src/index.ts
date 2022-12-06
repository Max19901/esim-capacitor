import { registerPlugin } from '@capacitor/core';

import type { EsimPlugin } from './definitions';

const Esim = registerPlugin<EsimPlugin>('Esim', {
  web: () => import('./web').then(m => new m.EsimWeb()),
});

export * from './definitions';
export { Esim };
