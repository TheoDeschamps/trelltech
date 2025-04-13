# 🎨 Charte Graphique — *TrellTech App*

## 🧭 Structure de l’Interface

### 🎨 Palette de Couleurs

| Élément               | Couleur             | Code Hex  |
|-----------------------|---------------------|-----------|
| Fond principal        | Gris très clair     | `#F9FAFB` |
| Texte principal       | Noir bleuté         | `#111827` |
| Primaire (Board)      | Bleu vibrant        | `#3B82F6` |
| Secondaire (Liste)    | Vert émeraude       | `#10B981` |
| Accent (Card)         | Violet profond      | `#6366F1` |
| Danger (Supprimer)    | Rouge doux          | `#F87171` |
| Fond Spinner / champ  | Gris clair          | `#E5E7EB` |

---

### 🖋️ Typographie

- **Font** : Roboto (police système Android)
- **Tailles** :
    - Titres : `18sp – 20sp` (gras)
    - Sous-titres / infos : `14sp – 16sp`
- **Style** : Clair, lisible, `textStyle="bold"` pour les titres

---

### 📦 Composants UI

#### ✅ CardView

- `cardCornerRadius`: `8dp – 12dp`
- `cardElevation`: `4dp – 6dp`
- `padding` interne : `16dp`
- `textColor`: `@android:color/white`
- Comportement : `clickable="true"`, `focusable="true"`
- **Disposition** : `LinearLayout horizontal`, actions à droite

#### ✅ Spinner

- `layout_height`: `48dp`
- `background`: `#E5E7EB`
- `textAlignment`: `center`
- `textColor`: `#111827`
- `popupBackground`: `#FFFFFF`
- `spinnerMode`: `dropdown`

#### ✅ FloatingActionButton (FAB)

- `backgroundTint`: `@color/teal_700`
- `tint`: `@android:color/white`
- `layout_gravity`: `bottom|end`
- `layout_margin`: `16dp`

#### ✅ Dialogs

- Fond : Blanc
- `padding`: `24dp`
- Champs : `EditText` avec `@drawable/edit_text_bg`
- Boutons :
    - **Positif** : Valide une action
    - **Négatif** : Annule

---

### 🧭 Navigation

- Architecture : **Navigation Component**
- Usage de `Spinner` pour la navigation **contextuelle** entre Boards/Lists
- Actions principales via **FloatingActionButton**

---

### 🧩 Icônes

| Action       | Icône par défaut                    | Couleur     |
|--------------|--------------------------------------|-------------|
| Ajouter      | `@android:drawable/ic_input_add`     | Teal        |
| Supprimer    | `@android:drawable/ic_delete`        | Rouge       |
| Modifier     | `@android:drawable/ic_menu_edit`     | Blanc       |

---

### 🪄 Style Global

> Une interface **moderne, lisible et colorée**, avec une hiérarchie visuelle claire entre Boards, Lists et Cards.

- 🎯 Minimaliste, mais visuellement différenciée
- ✅ Actions rapides (CRUD en 1 clic)
- 📱 Adapté à une expérience **mobile fluide**

---
