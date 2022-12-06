import { WebPlugin } from '@capacitor/core';

import type { EsimPlugin } from './definitions';

export class EsimWeb extends WebPlugin implements EsimPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
