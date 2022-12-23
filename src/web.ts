import { WebPlugin } from '@capacitor/core';

import type {IActivationCode, EsimPlugin, IeSimAvailable, IeSimStatus } from './definitions';

export class EsimWeb extends WebPlugin implements EsimPlugin {
  async isEnabled(): Promise<IeSimAvailable> {
    console.log('isEnabled', false);
    return {eSim: true, error: { errorCode: 0, errorMessage: 'web' }};
  }

  async eSimAdd(code: IActivationCode): Promise<IeSimAvailable | IeSimStatus> {
    console.log('eSimAdd', code.activationCode);
    return {eSim: true, error: { errorCode: 0, errorMessage: 'web' }};
  }
}
