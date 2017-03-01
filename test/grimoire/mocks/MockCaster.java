package grimoire.mocks;

import grimoire.spells.CasterInterface;
import grimoire.spells.Spell;

public class MockCaster implements CasterInterface {
    public Spell lastSpellPassedToCast;
    public boolean shouldCastSpell;

    @Override
    public boolean cast(Spell spell) {
        lastSpellPassedToCast = spell;
        return shouldCastSpell;
    }
}
