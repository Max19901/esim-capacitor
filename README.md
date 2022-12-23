# esim-capacitor

Add esim to device

## Install

```bash
npm install esim-capacitor
npx cap sync
```

## API

<docgen-index>

* [`isEnabled()`](#isenabled)
* [`eSimAdd(...)`](#esimadd)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isEnabled()

```typescript
isEnabled() => Promise<IeSimAvailable>
```

**Returns:** <code>Promise&lt;<a href="#iesimavailable">IeSimAvailable</a>&gt;</code>

--------------------


### eSimAdd(...)

```typescript
eSimAdd(code: IActivationCode) => Promise<IeSimAvailable | IeSimStatus>
```

| Param      | Type                                                        |
| ---------- | ----------------------------------------------------------- |
| **`code`** | <code><a href="#iactivationcode">IActivationCode</a></code> |

**Returns:** <code>Promise&lt;<a href="#iesimavailable">IeSimAvailable</a> | <a href="#iesimstatus">IeSimStatus</a>&gt;</code>

--------------------


### Interfaces


#### IeSimAvailable

| Prop        | Type                                            |
| ----------- | ----------------------------------------------- |
| **`eSim`**  | <code>boolean</code>                            |
| **`error`** | <code><a href="#esimerror">eSimError</a></code> |


#### eSimError

| Prop               | Type                |
| ------------------ | ------------------- |
| **`errorCode`**    | <code>number</code> |
| **`errorMessage`** | <code>string</code> |


#### IeSimStatus

| Prop          | Type                |
| ------------- | ------------------- |
| **`success`** | <code>string</code> |


#### IActivationCode

| Prop                 | Type                |
| -------------------- | ------------------- |
| **`activationCode`** | <code>string</code> |

</docgen-api>
